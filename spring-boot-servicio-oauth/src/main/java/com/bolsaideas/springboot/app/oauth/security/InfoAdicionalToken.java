package com.bolsaideas.springboot.app.oauth.security;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import com.bolsaideas.springboot.app.commons.usuarios.models.entity.Usuario;
import com.bolsaideas.springboot.app.oauth.services.IUsuarioService;

@Component
public class InfoAdicionalToken implements TokenEnhancer {
	
	@Autowired
	private IUsuarioService usuarioService;

	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		Map<String, Object> mapInfo = new HashMap<String, Object>();
		Usuario usuario = usuarioService.findByUsername(authentication.getName());
		mapInfo.put("nombre", usuario.getNombre());
		mapInfo.put("apellido", usuario.getApellido());
		mapInfo.put("email", usuario.getEmail());
		
		((DefaultOAuth2AccessToken)  accessToken).setAdditionalInformation(mapInfo);
		return accessToken;
	}

}
