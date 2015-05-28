/**
 * Created by JoonHo Son on 2014. 1. 9..
 */
;
(function ($) {
	/**
	 * 항목의 길이 반환
	 *
	 * @param options
	 */
	$.fn.inputLength = function (options) {
		// default option
		var defaultOptions = $.extend({
			                              isTrim: true
		                              }, options);

		var str = (defaultOptions.isTrim)? $.trim(this.val()): this.val();
		var koreanCount = 0;

		koreanCount = (encodeURI(str) + '%u').match(/%u/g).length - 1;

		return str.length + koreanCount;
	};

	/**
	 * 레이어 팝업
	 *
	 * @param options 옵션
	 */
	$.fn.popupLayer = function (options) {
		// default option
		var defaultOptions = $.extend({
			                              backgroundColor: '#000',
			                              opacity: 0.2
		                              }, options);

		var wrapper;

		if ($(document).find('#__popupWrapper').length == 0) {
			$('body').append('<div id="__popupWrapper"><div id="__popupBG"></div></div>');

			wrapper = $('#__popupWrapper');
		} else {
			wrapper = $('#__popupWrapper');
			wrapper.empty().append('<div id="__popupBG"></div>');
		}

		var target = (defaultOptions.isClone)? this.clone(true): this;

		$(wrapper).append(target);

		var bg = $('#__popupBG');

		$(wrapper).css('position', 'fixed').css('_position', 'absolute').css('top', '0').css('left', '0');
		$(wrapper).css('width', '100%').css('height', '100%').css('z-index', '300');

		$(bg).css('width', '100%').css('height', '100%').css('background-color',
		                                                     defaultOptions.backgroundColor).css('position',
		                                                                                         'absolute');
		$(bg).css('top', '0').css('left', '0').css('opacity', defaultOptions.opacity).css('z-index', '100');

		$(target).css('position', 'absolute').css('top', '50%').css('left', '50%').css('z-index', '101');
		$(target).css('background-color', '#fff');

		if (this.outerHeight() < $(document).height()) {
			$(target).css('margin-top', '-' + this.outerHeight() / 2 + 'px');
		} else {
			$(target).css('top', '0');
		}

		if (this.outerWidth() < $(document).width()) {
			$(target).css('margin-left', '-' + this.outerWidth() / 2 + 'px');
		} else {
			$(target).css('left', '0');
		}

		$(target).show();
		$(wrapper).show();

		$(target).find('.btnClose').bind('click', function (e) {
			$(wrapper).hide();

			e.preventDefault();
		});
	};

	$.fn.getLastDateWithObject = function (date) {
		if (date == null || typeof date == 'undefined') {
			return null;
		}

		return new Date(new Date(date.getFullYear(), date.getMonth() + 1, 1) - 1);
	};

	$.fn.getLastDate = function (date) {
		return $.getLastDateWithObject(date).getDate();
	};

	$.fn.getLastDay = function (date) {
		return $.getLastDateWithObject(date).getDay();
	};

	$.fn.getDateString = function (date) {
		return date.getFullYear + $.zeroFill(date.getMonth() + 1, 2) + $.zeroFill(date.getDate(), 2);
	};

	// 화면 가운데 정렬하기
	// 참고 : http://www.hackerex.com/Question/10/제이쿼리로DIV요소를화면가운데에놓기
	$.fn.center = function () {
		this.css('position', 'absolute');
		this.css('top', Math.max(0, (($(window).height() - $(this).outerHeight()) / 2) + $(window).scrollTop()) + 'px');
		this.css('left', Math.max(0, (($(window).width() - $(this).outerWidth()) / 2) + $(window).scrollLeft()) + 'px');

		return this;
	};

	// Window resize 시에도 가운데 정렬 할 수 있도록 추가.
	$.fn.centerWithResize = function () {
		var target = this;

		this.center();

		$(window).on('resize', function () {
			target.center();
		});
	};

	$.fn.shareTwitter = function (url) {
		var href = 'https://twitter.com/intent/tweet?url=' + encodeURIComponent(url);

		var pop = window.open(href, 'ShareTwitter', 'width=400,innerWidth=350,resizable=no,scrollbars=no,status=no');

		if (pop) {
			pop.focus();
		}
	};

	$.fn.shareFacebook = function (url) {
		var href = 'http://www.facebook.com/sharer/sharer.php?s=100';

		href += encodeURI('&p[url]') + '=' + encodeURIComponent(url);

		var pop = window.open(href, 'ShareFacebook', 'width=400,innerWidth=350,resizable=no,scrollbars=no,status=no');

		if (pop) {
			pop.focus();
		}
	};
})($);

$.extend(
	{
		zeroFill: function (targetValue, fullLength) {
			if (targetValue == null || typeof targetValue == 'undefined' || fullLength < 0) {
				return '';
			}

			var length = fullLength - targetValue.toString().length;
			var result = '';

			for (var i = 0; i < length; i++) {
				result += '0';
			}

			return result + targetValue.toString();
		}
	});