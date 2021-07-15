package br.com.caelum.carangobom.auth;

import java.io.IOException;
import java.util.Optional;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.caelum.carangobom.auth.token.TokenService;
import br.com.caelum.carangobom.user.User;
import br.com.caelum.carangobom.user.UserRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

public class FilterAuthToken extends OncePerRequestFilter {
	
	private TokenService tokenService;
	private UserRepository repository;

	public FilterAuthToken(TokenService tokenService, UserRepository repository) {
		this.tokenService = tokenService;
		this.repository = repository;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String token = getRequestToken(request);
		boolean isValid = tokenService.isValid(token);
		if (isValid) {
			authenticationClient(token);
		}
		filterChain.doFilter(request, response);
	}

	private void authenticationClient(String token) {
		Long userId = tokenService.getUserId(token);
		Optional<User> user = repository.findById(userId);
		if(user.isPresent()){
			UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}
	}

	private String getRequestToken(HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		if (token == null || token.isEmpty() || !token.startsWith("Bearer ")) {
			return null;
		}
		
		return token.substring(7, token.length());
	}

}
