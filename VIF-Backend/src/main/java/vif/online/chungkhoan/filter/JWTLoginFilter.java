package vif.online.chungkhoan.filter;

public class JWTLoginFilter {

	/*public JWTLoginFilter(String url, AuthenticationManager authManager) {
		super(new AntPathRequestMatcher(url));
		// TODO Auto-generated constructor stub
		setAuthenticationManager(authManager);
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {
		// TODO Auto-generated method stub
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		return getAuthenticationManager()
				.authenticate(new UsernamePasswordAuthenticationToken(username, password, Collections.emptyList()));
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {

		System.out.println("JWTLoginFilter.successfulAuthentication:");

		// Write Authorization to Headers of Response.
		TokenAuthenticationService.addAuthentication(response, authResult.getName());

		String authorizationString = response.getHeader("Authorization");
		System.out.println("Authorization String=" + authorizationString);
	}*/
}
