/**
 *
 */

(function($){

	var defaults = {
		speed: 700,
		pause: 4000,
		showItems: 1,
		mousePause: true,
		height: 0,
		animate: true,
		margin: 0,
		padding: 0,
		startPaused: false,
		now:0
	};

	var internal = {

		moveUp: function(state, attribs) {
			internal.animate(state, attribs, 'up');
		},

		moveDown: function(state, attribs){
			internal.animate(state, attribs, 'down');
		},

		animate: function(state, attribs, dir) {
			var height = state.itemHeight;
			var options = state.options;
			var el = state.element;
			var obj = el.children('ul');
			var selector = (dir === 'up') ? 'li:first' : 'li:last';

			el.trigger("lgRoll.beforeTick");

			var clone = obj.children(selector).clone(true);

			if(options.height > 0) height = obj.children('li:first').height();
			height += (options.margin) + (options.padding*2);

			if(dir==='down') obj.css('top', '-' + height + 'px').prepend(clone);

			if(attribs && attribs.animate) {
				if(state.animating) return;
				state.animating = true;
				var opts = (dir === 'up') ? {top: '-=' + height + 'px'} : {top: 0};
				obj.animate(opts, options.speed, function() {
					$(obj).children(selector).remove();
					$(obj).css('top', '0px');
					state.animating = false;
					el.trigger("lgRoll.afterTick");
					obj.find('li').css('font-weight','normal');
					obj.find('li').eq(0).css({
						'color': '#aaa',
						'font-size':'12px',
                        'height':'21px',
                        'padding':'3px 0'
					});
					obj.find('li').eq(1).css({
						'color': '#000',
						'font-size':'14px',
                        'height':'21px',
                        'padding':'8px 0'
					});
					obj.find('li').eq(2).css({
						'color': '#ce183e',
						'font-size':'22px',
						'font-weight':'bold',
                        'height':'28px',
                        'padding':'23px 0 20px'
					});
					obj.find('li').eq(3).css({
						'color': '#000',
						'font-size':'14px',
                        'height':'21px',
                        'padding':'8px 0'
					});
					obj.find('li').eq(4).css({
						'color': '#aaa',
						'font-size':'12px',
                        'height':'21px',
                        'padding':'3px 0'
					});
                    el.css('visibility', 'visible')
				});
			} else {
				obj.children(selector).remove();
				obj.css('top', '0px');
				el.trigger("lgRoll.afterTick");
                obj.find('li').css('font-weight','normal');
                obj.find('li').eq(0).css({
                    'color': '#aaa',
                    'font-size':'12px',
                    'height':'21px',
                    'padding':'3px 0'
                });
                obj.find('li').eq(1).css({
                    'color': '#000',
                    'font-size':'14px',
                    'height':'21px',
                    'padding':'8px 0'
                });
                obj.find('li').eq(2).css({
                    'color': '#ce183e',
                    'font-size':'22px',
                    'font-weight':'bold',
                    'height':'28px',
                    'padding':'23px 0 20px'
                });
                obj.find('li').eq(3).css({
                    'color': '#000',
                    'font-size':'14px',
                    'height':'21px',
                    'padding':'8px 0'
                });
                obj.find('li').eq(4).css({
                    'color': '#aaa',
                    'font-size':'12px',
                    'height':'21px',
                    'padding':'3px 0'
                });
                el.css('visibility', 'visible')
			}
			if(dir==='up') clone.appendTo(obj);
		},

		nextUsePause: function() {
			var state = $(this).data('state');
			var options = state.options;
			if(state.isPaused || state.itemCount < 2) return;
			methods.next.call( this, {animate:options.animate} );
		},

		startInterval: function() {
			var state = $(this).data('state');
			var options = state.options;
			var initThis = this;
			state.intervalId = setInterval(function(){
				internal.nextUsePause.call( initThis );
			}, options.pause);
		},

		stopInterval: function() {
			var state = $(this).data('state');
			if(!state) return;
			if(state.intervalId) clearInterval(state.intervalId);
			state.intervalId = undefined;
		},

		restartInterval: function() {
			internal.stopInterval.call(this);
			internal.startInterval.call(this);
		}
	};

	var methods = {

		init: function(options) {
			methods.stop.call(this);
			var defaultsClone = jQuery.extend({}, defaults);
			var options = $.extend(defaultsClone, options);
			var el = $(this);
			var state = {
				itemCount: el.children('ul').children('li').length,
				itemHeight: 0,
				itemMargin: 0,
				element: el,
				animating: false,
				options: options,
				isPaused: (options.startPaused) ? true : false,
				pausedByCode: false
			};
			$(this).data('state', state);

			el.css({overflow: 'hidden', position: 'relative'})
					.children('ul').css({position: 'absolute', margin: 0, padding: 0})
					//.children('li').css({margin: options.margin, padding: options.padding});

			if(isNaN(options.height) || options.height === 0)
			{
				el.children('ul').children('li').each(function(){
					var current = $(this);
					if(current.height() > state.itemHeight)
						state.itemHeight = current.height();
				});
				el.children('ul').children('li').each(function(){
					var current = $(this);
					current.height(state.itemHeight);
				});
				var box = (options.margin) + (options.padding * 2);
				el.height(((state.itemHeight + box) * options.showItems) + options.margin);
			}
			else
			{
				el.height(options.height);
			}

			var initThis = this;
			if(!options.startPaused) {
				internal.startInterval.call( initThis );
			}

			if(options.mousePause)
			{
				el.bind("mouseenter", function () {
					if (state.isPaused === true) return;
					state.pausedByCode = true;
					internal.stopInterval.call( initThis );
					methods.pause.call( initThis, true );
				}).bind("mouseleave", function () {
					if (state.isPaused === true && !state.pausedByCode) return;
					state.pausedByCode = false;
					methods.pause.call(initThis, false);
					internal.startInterval.call( initThis );
				});
			}
			internal.moveUp(state,  {animate:true});
		},

		pause: function(pauseState) {
			var state = $(this).data('state');
			if(!state) return undefined;
			if(state.itemCount < 2) return false;
			state.isPaused = pauseState;
			var el = state.element;
			if(pauseState) {
				$(this).addClass('paused');
				el.trigger("lgRoll.pause");
			}
			else {
				$(this).removeClass('paused');
				el.trigger("lgRoll.resume");
			}
		},

		next: function(attribs) {
			var state = $(this).data('state');
			if(!state) return undefined;
			if(state.animating || state.itemCount < 2) return false;
			internal.restartInterval.call( this );
			internal.moveUp(state, attribs);
		},

		prev: function(attribs) {
			var state = $(this).data('state');
			if(!state) return undefined;
			if(state.animating || state.itemCount < 2) return false;
			internal.restartInterval.call( this );
			internal.moveDown(state, attribs);
		},

		stop: function() {
			var state = $(this).data('state');
			if(!state) return undefined;
			internal.stopInterval.call( this );
		},

		remove: function() {
			var state = $(this).data('state');
			if(!state) return undefined;
			internal.stopInterval.call( this );
			var el = state.element;
			el.unbind();
			el.remove();
		}
	};

	$.fn.lgRoll = function( method ) {
		if ( methods[method] ) {
			return methods[method].apply( this, Array.prototype.slice.call( arguments, 1 ));
		} else if ( typeof method === 'object' || ! method ) {
			return methods.init.apply( this, arguments );
		} else {
			$.error( 'Method ' +  method);
		}
	};
})(jQuery);
	$(function(){
		$('#roll-container').lgRoll({
			speed: 500,
			pause: 3000,
			animate:true,
            padding:0,
			//animation: 'fade',
			mousePause: true,
			showItems: 5
		});
	});
    $('.btn-close, .pop-bg').on('click',function(){
        close_pop();
        return false;
    });

    /* 레이어 열기 */
    var load_pop = function(target){
        $(target).show();
        $('.pop-layer').show().css('margin-left',-($('.pop-layer').width()/2));
        $('.pop-layer').css('top',$(window).scrollTop()+($(window).height()-$('.pop-layer').outerHeight())/2)
        $('.pop-bg ').show().height($(document).height());
        $(window).bind('mousewheel',function(){
            return false;
        })
        return false;
    }

    /* 레이어 닫기 */
    var close_pop = function(){
        $('.pop-cont').hide();
        $('.pop-layer').hide();
        $('.pop-bg ').hide();
        $(window).unbind('mousewheel')
    }

    $(window).on('resize',function(){
        $('.pop-bg ').height($(document).height());
        $('.pop-layer').css('margin-left',-($('.pop-layer').width()/2));
    });
