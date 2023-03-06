<template>
    <!-- Header -->
    <header class="header header-page">
        <div class="header-fixed">
            <nav class="navbar navbar-expand-lg header-nav scroll-sticky">
                <div class="container">
                    <navbar></navbar>
                    <div class="main-menu-wrapper">
                        <menuheader></menuheader>
                        <mainnav></mainnav>
                    </div>
                  <headerpage v-if="data.isLogin"></headerpage>
                  <signpages v-else ></signpages>
                </div>
            </nav>
        </div>
    </header>
    <!-- /Header -->	
</template>
<script>
  import Vue from 'vue'
  import {useStore} from "vuex";
  export default {
    components: {
        
    },setup() {
      const store = useStore();
      const data = ({
        // isLogin: store.getters["userStore/getIsLogin"],
        isLogin: store.state.userStore.isLogin,
        isValidToken: store.state.userStore.isValidToken,
      });
      return {data};
    },
    mounted() {
   // Content div min height set
	
	function resizeInnerDiv() {
		var height = $(window).height();	
		var header_height = $(".header").height();
		var footer_height = $(".footer").height();
		var setheight = height - header_height;
		var trueheight = setheight - footer_height;
		$(".content").css("min-height", trueheight);
	}
	
	if($('.content').length > 0 ){
		resizeInnerDiv();
	}

	$(window).resize(function(){
		if($('.content').length > 0 ){
			resizeInnerDiv();
		}
	});
    $('.header-fixed').append('<div class="sidebar-overlay"></div>');
	$(document).on('click', '#mobile_btn', function() {
		$('main-wrapper').toggleClass('slide-nav');
		$('.sidebar-overlay').toggleClass('opened');
		$('html').addClass('menu-opened');
		return false;
	});
	
	$(document).on('click', '.sidebar-overlay', function() {
		$('html').removeClass('menu-opened');
		$(this).removeClass('opened');
		$('main-wrapper').removeClass('slide-nav');
	});
	
	$(document).on('click', '#menu_close', function() {
		$('html').removeClass('menu-opened');
		$('.sidebar-overlay').removeClass('opened');
		$('main-wrapper').removeClass('slide-nav');
	});
	if($(window).width() <= 991){
	var Sidemenu = function() {
		this.$menuItem = $('.main-nav a');
	};
	
	function init() {
		var $this = Sidemenu;
		$('.main-nav a').on('click', function(e) {
			if($(this).parent().hasClass('has-submenu')) {
				e.preventDefault();
			}
			if(!$(this).hasClass('submenu')) {
				$('ul', $(this).parents('ul:first')).slideUp(350);
				$('a', $(this).parents('ul:first')).removeClass('submenu');
				$(this).next('ul').slideDown(350);
				$(this).addClass('submenu');
			} else if($(this).hasClass('submenu')) {
				$(this).removeClass('submenu');
				$(this).next('ul').slideUp(350);
			}
		});
	}

	// Sidebar Initiate
	
	init();
	}
	$(window).scroll(function(){
		var sticky = $('.scroll-sticky'),
		  scroll = $(window).scrollTop();

		if (scroll >= 100) sticky.addClass('add-header-bg');
		else sticky.removeClass('add-header-bg');
	});
    },
    
  }
</Script>