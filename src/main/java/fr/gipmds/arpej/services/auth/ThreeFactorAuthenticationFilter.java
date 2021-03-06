package fr.gipmds.arpej.services.auth;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;;


/**
 * Custom {@link UsernamePasswordAuthenticationFilter} that includes three-factor
 * authentication. A user has a nom(the username), prenom and a siret with
 * with a password, and the three "username" are returned
 * through the <code>obtainUsername()</code> method as a colon-delimited string.
 * The delimiter string (a colon by default) can be customized with the
 * <code>setDelimiter()</code> method.
 * 
 */
public class ThreeFactorAuthenticationFilter extends
		UsernamePasswordAuthenticationFilter {
	
	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass().getName());
	
	private String nom = "j_nom";
	private String prenom = "j_prenom";
	private String siret = "j_siret";
	private String delimiter = ":";

	/**
	 * Given an {@link HttpServletRequest}, this method extracts the username
	 * and the extra input values and returns a combined username string of
	 * those values separated by the delimiter string.
	 * 
	 * @param request
	 *            The {@link HttpServletRequest} containing the HTTP request
	 *            variables from which the username client domain values can be
	 *            extracted
	 */
	@Override
	protected String obtainUsername(HttpServletRequest request) {
		String username = request.getParameter(getNom());
		String extraPrenom = request.getParameter(getPrenom());
		String extraSiret = request.getParameter(getSiret());

		String combinedUsername = 	username +  getDelimiter() 
								 	+ extraPrenom +  getDelimiter() 
								 	+ extraSiret;
		return combinedUsername;
	}
	
	

	/* (non-Javadoc)
	 * @see org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter#successfulAuthentication(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.springframework.security.core.Authentication)
	 * Here the code after the authentication
	 */
	
	@Override
	protected void successfulAuthentication(HttpServletRequest request,
			HttpServletResponse response, Authentication authResult)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		super.successfulAuthentication(request, response, authResult);
		LOGGER.debug("Utilisateur authenticated {}",authResult);
	}
	


	/**
	 * @return The delimiter string used to separate the username and extra
	 *         input values in the string returned by
	 *         <code>obtainUsername()</code>
	 */
	public String getDelimiter() {
		return this.delimiter;
	}

	/**
	 * @param delimiter
	 *            The delimiter string used to separate the username and extra
	 *            input values in the string returned by
	 *            <code>obtainUsername()</code>
	 */
	public void setDelimiter(String delimiter) {
		this.delimiter = delimiter;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getSiret() {
		return siret;
	}

	public void setSiret(String siret) {
		this.siret = siret;
	}
	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}
}