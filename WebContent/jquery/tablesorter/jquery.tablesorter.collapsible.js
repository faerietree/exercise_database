/**
 * @author Dan G. Switzer II, J.R.I.B.-Wein (compatibility with grouping
 * + dynamic content update (as events have been added after new content
 * was created/appended))
 */

	/* declare defaults */
	var defaults = {//Note: collapsed is set as default value in tablesorter.mod.js in group function.
		selector: "td.collapsible"        // the default selector to use
		, toggleAllSelector: "expand_all"           // the selector to use to attach the collapsibleToggle() function
		, classChildRow: "expand-child"   // define the "child row" css class
		, classCollapse: "collapsed"      // define the "collapsed" css class
		, classExpand: "expanded"         // define the "expanded" css class
		, showCollapsed: false            // specifies if the default css state should show collapsed (use this if you want to collapse the rows using CSS by default)
		, collapse: true                 // if true will force rows to collapse via JS (use this if you want JS to force the rows collapsed)
		, fx: {hide:"hide",show:"show"}   // the fx to use for showing/hiding elements (fx do not work correctly in IE6)
		, addAnchor: "append"             // how should we add the anchor? append, wrapInner, etc
		, textExpand: "Expand All"        // the text to show when expand all
		, textCollapse: "Collapse All"    // the text to show when collase all
	}, bHideParentRow = ($.browser.msie && ($.browser.version <= 7));





var toggleSwitch_callback = function (el, settings, bHideParentRow){
    settings = settings || defaults;
					var $self = $(el), 
						$tr = $self.parent().parent(), 
						$trc = $tr.next(), 
						bIsCollapsed = $self.hasClass(settings.classCollapse);
					// change the css class
					$self[bIsCollapsed ? "removeClass" : "addClass"](settings.classCollapse)[!bIsCollapsed ? "removeClass" : "addClass"](settings.classExpand);
// alert('this: ' + this + '  el: ' + el + '   settings: ' + settings);
					// iterate over all following table rows:
					while( $trc.hasClass(settings.classChildRow) ){
						if( bHideParentRow ){
							// get the tablesorter options
							var ts_config = $.data(self[0], "tablesorter");
							// hide/show the row
							$trc[bIsCollapsed ? settings.fx.hide : settings.fx.show]();
							
							// if we have the ts settings, we need to up zebra stripping if active
							if( !bIsCollapsed && ts_config ){
								if( $tr.hasClass(ts_config.widgetZebra.css[0]) ) $trc.addClass(ts_config.widgetZebra.css[0]).removeClass(ts_config.widgetZebra.css[1]);
								else if( $tr.hasClass(ts_config.widgetZebra.css[1]) ) $trc.addClass(ts_config.widgetZebra.css[1]).removeClass(ts_config.widgetZebra.css[0]);
							}
						}
						// show all the table cells
						//$("td", $trc)[bIsCollapsed ? settings.fx.hide : settings.fx.show]();
						//($trc.hasClass(settings.classCollapse) ? settings.fx.hide : settings.fx.show)();
						var trIsExpanded = $trc.hasClass(settings.classExpand);
						// toggle class:
						trIsExpanded ? $trc.removeClass(settings.classExpand).addClass(settings.classCollapse) 
								: $trc.removeClass(settings.classCollapse).addClass(settings.classExpand)
										.removeClass(settings.classCollapse);//<-double because else at first no expanding effect.
						// get the next row
						$trc = $trc.next();
					}
					return false;
				};





(function ($){
	$.fn.collapsible = function (sel, options){
		var self = this, 
                bIsElOpt = (sel && sel.constructor == Object),
		settings = $.extend({}, defaults, bIsElOpt ? sel : options);
		
		if( !bIsElOpt ) settings.selector = sel;
		// make sure that if we're forcing to collapse, that we show the collapsed css state
		if( settings.collapse ) settings.showCollapsed = true;
		
		return this.each(function (){
			var $td = $(settings.selector, this),
			// look for existing anchors
			$a = $td.find("a");
			
			// if a "toggle all" selector has been specified, find and attach the behavior
			if( settings.toggleAllSelector.length > 0 ) $(this).find(settings.toggleAllSelector).collapsibleToggle(this);
			
			// if no anchors, create them
			if( $a.length == 0 ) $a = $td[settings.addAnchor]('<a href="javascript:;" class="' + settings[settings.showCollapsed ? "classCollapse" : "classExpand"] + '"></a>').find("a");
//alert("Now binding function to click event of toggle a-element.");
			$a.bind("click", function () { toggleSwitch_callback(this, settings, bHideParentRow); }
                        );
			
			// if not IE and we're automatically collapsing rows, collapse them now
			if( settings.collapse && !bHideParentRow ){
				$td
					// get the tr element
					.parent()
					.each(function (){
						var $tr = $(this).next();
						while( $tr.hasClass(settings.classChildRow) ){
							// hide each table cell
							$tr = $tr.find("td").hide().end().next();
						}
					});
			}

			// if using IE, we need to hide the table rows
			if( settings.showCollapsed && bHideParentRow ){
				$td
					// get the tr element
					.parent()
					.each(function (){
						var $tr = $(this).next();
						while( $tr.hasClass(settings.classChildRow) ){
							$tr = $tr.hide().next();
						}
					});
			}
		});
	}
	
	$.fn.collapsibleToggle = function(table, options){
		var settings = $.extend({}, defaults, options), $table = $(table);

		// attach the expand behavior to all options
		this.toggle(
			// expand all entries
			function (){
				var $el = $(this);
				$el.addClass(settings.classExpand).removeClass(settings.classCollapse);
				if( !$el.is("td,th") )
					$el[$el.is(":input") ? "val" : "html"](settings.textCollapse);
				$(settings.selector + " a", $table).removeClass(settings.classExpand).click();
			}
			// collapse all entries
			, function (){
				var $el = $(this);
				$el.addClass(settings.classCollapse).removeClass(settings.classExpand);
				if( !$el.is("td,th") )
					$el[$el.is(":input") ? "val" : "html"](settings.textExpand);
				$(settings.selector + " a", $table).addClass(settings.classExpand).click();
			}
		);
		
		// update text
		if( !this.is("td,th") ) this[this.is(":input") ? "val" : "html"](settings.textExpand);
		
		return this.addClass(settings.classCollapse).removeClass(settings.classExpand);
  }

})(jQuery);
