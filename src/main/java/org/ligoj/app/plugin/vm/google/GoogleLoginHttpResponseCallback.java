package org.ligoj.app.plugin.vm.google;

import java.io.IOException;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.message.BasicHeader;
import org.ligoj.bootstrap.core.curl.CurlRequest;
import org.ligoj.bootstrap.core.curl.DefaultHttpResponseCallback;

/**
 * vCloud login response handler saving the "x-vcloud-authorization".
 */
public class GoogleLoginHttpResponseCallback extends DefaultHttpResponseCallback {

	@Override
	public boolean onResponse(final CurlRequest request, final CloseableHttpResponse response) throws IOException {
		final boolean result = super.onResponse(request, response);
		if (result) {
			// Success request, get the authentication token
			final String token = ObjectUtils
					.defaultIfNull(response.getFirstHeader("x-vcloud-authorization"), new BasicHeader("", null))
					.getValue();

			// Save this token in the associated processor for next requests
			((GoogleCurlProcessor) request.getProcessor()).setToken(token);
		}
		return result;
	}
}
