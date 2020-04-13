/**
 * @author  Wu,Yang
 * @version 2010-08-27
 * @desc 计算距离 
 */
var markersArrayForCalc = [];
var polylineForCalc;
var polylineArrayForCalc =[];
var markerLabelArrayForCalc = [];
var listenerHandleArrayForCalc = [];

var listenerHandle;
function add() {
	var listenerHandle = google.maps.event.addListener(map, 'click',
			function(event) {
				addMarker(event.latLng);
			}); 
	listenerHandleArrayForCalc.push(listenerHandle);
	addAreaListernerHandle();
}

function addAreaListernerHandle(){
	if (polylineForCalcWithSum){
		for (i in polylineForCalcWithSum) {
			var listenerHandle = google.maps.event.addListener(polylineForCalcWithSum[i], 'click',
					function(event) {
						addMarker(event.latLng);
		    }); 
			listenerHandleArrayForCalc.push(listenerHandle);
		}
	}
}

function remove() {
	if (listenerHandleArrayForCalc) {
		for (i in listenerHandleArrayForCalc) {
			google.maps.event.removeListener(listenerHandleArrayForCalc[i]);
		}
		listenerHandleArrayForCalc.length = [];
	}
	
}

function addMarker(location) {
	var marker = new google.maps.Marker({
		position: location,
		map: map,
		icon: 'http://labs.google.com/ridefinder/images/mm_20_red.png',
		draggable: true
	});

	var markerLabel = new Label({ map: map });
	markerLabel.bindTo('position', marker, 'position');

	markersArrayForCalc.push(marker);
	markerLabelArrayForCalc.push(markerLabel);

	drawOverlay();

	google.maps.event.addListener(marker, 'drag', function() {
		drawOverlay();
	});

}

//画线,计算线的距离
function drawOverlay() {
	var flightPlanCoordinates = [];
	if (markersArrayForCalc) {
		for (i in markersArrayForCalc) {
			flightPlanCoordinates.push(markersArrayForCalc[i].getPosition());
		}

		if (markersArrayForCalc.length <= 1) {
			polylineForCalc = new google.maps.Polyline({
				path: flightPlanCoordinates,
				strokeColor: "#FF0000",
				strokeOpacity: 0.5,
				strokeWeight: 4
			});
			polylineForCalc.setMap(map);
			polylineArrayForCalc.push(polylineForCalc);
		} else {
			polylineForCalc.setPath(flightPlanCoordinates);
		}
		$("#total_km").empty().html("<b>" + (polylineForCalc.getLength()/1000).toFixed(3) + " Km<b>");
	}

	if (markerLabelArrayForCalc) {
		for (i in markerLabelArrayForCalc) {
			if (i == 0 || i == (markerLabelArrayForCalc.length - 1)) {
				markerLabelArrayForCalc[i].set('text',(polylineForCalc.getLength()/1000).toFixed(3) + " Km");
			}
			else {
				markerLabelArrayForCalc[i].set('text',"");
			}
		}
	}
		
}

//清空地图
function clearOverlays() {
	if (markersArrayForCalc) {
		for (i in markersArrayForCalc) {
			markersArrayForCalc[i].setMap(null);
		}
		markersArrayForCalc.length = [];
	}

	if (polylineArrayForCalc) {
		for (i in polylineArrayForCalc) {
			polylineArrayForCalc[i].setMap(null);
		}
		polylineArrayForCalc = [];
	}
	if (markerLabelArrayForCalc) {
		for (i in markerLabelArrayForCalc) {
			markerLabelArrayForCalc[i].setMap(null);
		}
		markerLabelArrayForCalc = [];
	}

}