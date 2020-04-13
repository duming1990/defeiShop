jQuery(document).ready(function($) {

	var _SlideshowTransitions = [

		{
			$Duration: 1200,
			y: -0.3,
			$Cols: 2,
			$SlideOut: true,
			$ChessMode: {
				$Column: 12
			},
			$Easing: {
				$Top: $JssorEasing$.$EaseInCubic,
				$Opacity: $JssorEasing$.$EaseLinear
			},
			$Opacity: 2
		},

		{
			$Duration: 1000,
			y: 4,
			$Zoom: 11,
			$Easing: {
				$Top: $JssorEasing$.$EaseInCubic,
				$Zoom: $JssorEasing$.$EaseInCubic,
				$Opacity: $JssorEasing$.$EaseOutQuad
			},
			$Opacity: 2
		},

		{
			$Duration: 1200,
			y: 1,
			$Rows: 2,
			$Zoom: 1,
			$ChessMode: {
				$Row: 15
			},
			$Easing: {
				$Top: $JssorEasing$.$EaseInCubic,
				$Zoom: $JssorEasing$.$EaseInCubic,
				$Opacity: $JssorEasing$.$EaseLinear
			},
			$Assembly: 2049,
			$Opacity: 2
		},

		{
			$Duration: 1500,
			x: -1,
			y: 0.5,
			$Delay: 60,
			$Cols: 8,
			$Rows: 4,
			$Formation: $JssorSlideshowFormations$.$FormationSquare,
			$Easing: {
				$Left: $JssorEasing$.$EaseSwing,
				$Top: $JssorEasing$.$EaseInWave
			},
			$Assembly: 260,
			$Round: {
				$Top: 1.5
			}
		},

		{
			$Duration: 1800,
			x: 1,
			$Delay: 30,
			$Cols: 10,
			$Rows: 5,
			$Clip: 15,
			$During: {
				$Left: [0.3, 0.7]
			},
			$Formation: $JssorSlideshowFormations$.$FormationStraightStairs,
			$Easing: {
				$Left: $JssorEasing$.$EaseInOutExpo,
				$Clip: $JssorEasing$.$EaseInOutQuad
			},
			$Assembly: 260,
			$Outside: true,
			$Round: {
				$Top: 0.8
			}
		},

		{
			$Duration: 1500,
			x: 0.3,
			y: -0.3,
			$Delay: 20,
			$Cols: 8,
			$Rows: 4,
			$Clip: 15,
			$During: {
				$Left: [0.1, 0.9],
				$Top: [0.1, 0.9]
			},
			$SlideOut: true,
			$Formation: $JssorSlideshowFormations$.$FormationSwirl,
			$Easing: {
				$Left: $JssorEasing$.$EaseInJump,
				$Top: $JssorEasing$.$EaseInJump,
				$Clip: $JssorEasing$.$EaseOutQuad
			},
			$Assembly: 260,
			$Outside: true,
			$Round: {
				$Left: 0.8,
				$Top: 2.5
			}
		},

		{
			$Duration: 1200,
			x: 2,
			y: 1,
			$Cols: 2,
			$Zoom: 11,
			$Rotate: 1,
			$ChessMode: {
				$Column: 15
			},
			$Easing: {
				$Left: $JssorEasing$.$EaseInCubic,
				$Top: $JssorEasing$.$EaseInCubic,
				$Zoom: $JssorEasing$.$EaseInCubic,
				$Opacity: $JssorEasing$.$EaseOutQuad,
				$Rotate: $JssorEasing$.$EaseInCubic
			},
			$Assembly: 2049,
			$Opacity: 2,
			$Round: {
				$Rotate: 0.7
			}
		},

		{
			$Duration: 1000,
			$Rows: 6,
			$Clip: 4,
			$Move: true
		},

		{
			$Duration: 800,
			$Delay: 150,
			$Cols: 10,
			$Clip: 1,
			$Move: true,
			$Formation: $JssorSlideshowFormations$.$FormationCircle,
			$Easing: $JssorEasing$.$EaseInBounce,
			$Assembly: 264
		},

		{
			$Duration: 600,
			x: -1,
			y: 1,
			$Delay: 30,
			$Cols: 8,
			$Rows: 4,
			$Formation: $JssorSlideshowFormations$.$FormationStraightStairs,
			$Easing: {
				$Left: $JssorEasing$.$EaseInQuart,
				$Top: $JssorEasing$.$EaseInQuart,
				$Opacity: $JssorEasing$.$EaseLinear
			},
			$Opacity: 2
		}

	];

	var options = {

		$AutoPlay: true,

		$SlideshowOptions: {

			$Class: $JssorSlideshowRunner$,

			$Transitions: _SlideshowTransitions,

			$TransitionsOrder: 1,

			$ShowLink: true

		},

		$ArrowNavigatorOptions: {

			$Class: $JssorArrowNavigator$

		},

		$BulletNavigatorOptions: {

			$Class: $JssorBulletNavigator$

		}

	};

	var jssor_slider1 = new $JssorSlider$('slider1_container', options);

});