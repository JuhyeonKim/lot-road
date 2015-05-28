package com.nagesoft.postcode.web.filter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.displaytag.tags.TableTagParameters;
import org.springframework.web.filter.OncePerRequestFilter;

import com.nagesoft.postcode.core.Constants;
import com.nagesoft.core.support.NGPagerTool;
import com.nagesoft.core.support.NGPagination;


public class PagingFilter extends OncePerRequestFilter {

	private String encoding = "UTF-8";

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	/**
	 * Filter의 메인 메소드에 해당한다.
	 *
	 * @see org.springframework.web.filter.OncePerRequestFilter#doFilterInternal(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse, javax.servlet.FilterChain)
	 */
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {

		String contentType = request.getContentType();
		String servletPath = request.getServletPath();

		NGPagination.resetAll();

		if (contentType != null && contentType.contains("multipart")
				&& servletPath.contains("/assets/")) {
			// contentType 이 파일 첨부라면 스킵한다.
			chain.doFilter(request, response);
		} else {
			String currentPage = request.getParameter(Constants.CURRENT_PAGE);
			String dataPerPage = request.getParameter(Constants.DATA_PER_PAGE);
			String linkPerPage = request.getParameter(Constants.LINK_PER_PAGE);
			String orderKey = request.getParameter(Constants.ORDER_KEY);
			String orderValue = request.getParameter(Constants.ORDER_VALUE);

			if (StringUtils.isEmpty(currentPage)) {
				NGPagination.currentPage.set(1);
			} else {
				try {
					NGPagination.currentPage.set(new Integer(currentPage));
				} catch (Exception ignore) {
					NGPagination.currentPage.set(1);
				}
			}

			if (StringUtils.isEmpty(dataPerPage)) {
				NGPagination.dataPerPage.set(Constants.DEFAULT_DATA_PER_PAGE);
			} else {
				try {
					NGPagination.dataPerPage.set(new Integer(dataPerPage));
				} catch (Exception ignore) {
					NGPagination.dataPerPage.set(Constants.DEFAULT_DATA_PER_PAGE);
				}
			}

			if (StringUtils.isEmpty(linkPerPage)) {
				NGPagination.linkPerPage.set(Constants.DEFAULT_PAGE_LINK_COUNT);
			} else {
				try {
					NGPagination.linkPerPage.set(new Integer(linkPerPage));
				} catch (Exception ignore) {
					NGPagination.linkPerPage
							.set(Constants.DEFAULT_PAGE_LINK_COUNT);
				}
			}

			NGPagination.queryString.set(extractQueryString(request));


			request.setAttribute("pager", new NGPagerTool());
			request.setAttribute("cond", getCondMap(request));

			chain.doFilter(request, response);
		}
	}

	/**
	 * 파라미터로 부터 query string 을 추출한다. 파라미터가 없을 경우에는 "?" 를 반환 파라미터가 있을 경우에는 "?" 로
	 * 시작해서 "&" 로 끝나는 문자열을 반환
	 *
	 * @param request
	 *            the request
	 *
	 * @return the string
	 */
	@SuppressWarnings("rawtypes")
	public String extractQueryString(HttpServletRequest request) {
		StringBuilder sb = new StringBuilder("?");

		Enumeration enumeration = request.getParameterNames();
		while (enumeration.hasMoreElements()) {
			String key = (String) enumeration.nextElement();

			// Constants.CURRENT_PAGE 이거나 Constants.DATA_PER_PAGE 인 경우는 skip
			if (StringUtils.equals(Constants.CURRENT_PAGE, key)
					|| StringUtils.equals(Constants.DATA_PER_PAGE, key)) {
				continue;
			}

			if (key.startsWith("q.") || key.startsWith("q.commonSelect")
					|| key.startsWith("q.commonText")
					|| StringUtils.equals(key, "skin")) {
				String[] values = request.getParameterValues(key);

				for (String value : values) {
					try {
						sb.append(key).append("=")
								.append(URLEncoder.encode(value, encoding))
								.append("&");
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
				}
			}
		}

		if ("?".equals(sb.toString()) && !Constants.IS_USE_PARAM_KEY) {
			enumeration = request.getParameterNames();
			while (enumeration.hasMoreElements()) {
				String key = (String) enumeration.nextElement();

				// Constants.CURRENT_PAGE 이거나 Constants.DATA_PER_PAGE 인 경우는 skip
				if (StringUtils.equals(Constants.CURRENT_PAGE, key)
						|| StringUtils.equals(Constants.DATA_PER_PAGE, key)) {
					continue;
				}

				String[] values = request.getParameterValues(key);

				for (String value : values) {
					try {
						sb.append(key).append("=")
								.append(URLEncoder.encode(value, encoding))
								.append("&");
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return sb.toString();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private Map getCondMap(HttpServletRequest request) {
		Enumeration enumeration = request.getParameterNames();
		Map condMap = new HashMap();

		while (enumeration.hasMoreElements()) {
			String key = (String) enumeration.nextElement();
			if (key.startsWith(Constants.CONDITION_PARAM_KEY)) {
				String[] values = (String[]) request.getParameterValues(key);
				condMap.put(
						key.substring(Constants.CONDITION_PARAM_KEY.length()),
						values[0]);
			} else if (!Constants.IS_USE_PARAM_KEY) {
				String[] values = (String[]) request.getParameterValues(key);
				condMap.put(key, values[0]);
			}
		}

		if (request.getParameter(TableTagParameters.PARAMETER_EXPORTING) != null) {
			condMap.put("NOPAGING", "true");
		}

		return condMap;
	}
}
