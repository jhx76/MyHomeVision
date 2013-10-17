$(function() {
	var selectedFunction = false;
	
	var locationList = new Array();
	var tmpLocation = new Array();	
	
	function loadLocations() {
		
	}
	
	$('#pallierContentList .LocationList').accordion();
	
	$(document).keyup(function(event) {
		if(event.keyCode == 27) { //ESC
			selectedFunction = false;
			tmpLocation = new Array();
			drawLocations();
			$('.ui-icon').css("border", "");
			$('.ui-icon').css("opacity", "");
		}
	});
	
	function testCollision(x1, y1, x2, y2, tolerence) {
		var xmin = x1 - tolerence;
		var xmax = x1 + tolerence;
		var ymin = y1 - tolerence;
		var ymax = y1 + tolerence;
		if((x2 > xmin && x2 < xmax) && (y2 > ymin && y2 < ymax))
			return true;
		return false;
	};
	
	function drawLocations() {
		$('#home_panel').clearCanvas();
		for(var iloc = 0; iloc < locationList.length; iloc++) {
			var location = locationList[iloc];
			for(var i = 0; i < location.length - 1; i++) {
				$('#home_panel').drawLine({
					strokeStyle: "#000",
					strokeWidth: 1,
					x1: location[i].x, y1: location[i].y,
					x2: location[i+1].x, y2: location[i+1].y
				});
			}
		}
		
		for(var i = 0; i < tmpLocation.length-1; i++) {
			$('#home_panel').drawLine({
				strokeStyle: "#000",
				strokeWidth: 1,
				x1: tmpLocation[i].x, y1: tmpLocation[i].y,
				x2: tmpLocation[i+1].x, y2: tmpLocation[i+1].y
			});
		}
	}
	
	function locationValidation() {
		var locationName = prompt("C est quoi le petit nom de la location ?? :)");
		if(locationName != null && locationName != "") {
			var l = $('#pallierContentList .LocationList');
			l.append('<h3>'+locationName+'<span class="ui-icon ui-icon-trash"></span></h3>');
			for(var i = 0; i < tmpLocation.length; i++) {
				l.append('<div>x: '+tmpLocation[i].x+'; y: '+tmpLocation[i].y+'</div>');
			}
			locationList.push(tmpLocation);
		}
		tmpLocation = new Array();
	}
	
	$('.ui-icon-extlink').click(function() {
		selectedFunction = "draw_location";
		$(this).css("border", "1px solid #1877D5");
		//$(this).css("background", "#84BEFD");
		$(this).css("opacity", "0.3");
	});
	
	$('#home_panel').click(function(event) {
		if(selectedFunction == "draw_location") {
			var posX = event.pageX - $(this).offset().left;
			var posY = event.pageY - $(this).offset().top;
			if(tmpLocation.length > 0 && testCollision(tmpLocation[0].x, tmpLocation[0].y, posX, posY, 10)) {
				tmpLocation.push({x: tmpLocation[0].x, y: tmpLocation[0].y});
				locationValidation();
			}
			else { tmpLocation.push({x: Math.round(posX), y: Math.round(posY)}); }
			drawLocations();
		}
	});
	
	$('#home_panel').mousemove(function(event) {
		if(selectedFunction == "draw_location") {
			var posX = event.pageX - $(this).offset().left;
			var posY = event.pageY - $(this).offset().top;
			$('#mousemovereport').html(posX+'px, '+posY+'px;');
			if(tmpLocation.length > 0) {
				drawLocations();
				if(testCollision(tmpLocation[0].x, tmpLocation[0].y, posX, posY, 10)) {
					$("canvas").drawArc({
					  strokeStyle: "#009",
					  strokeWidth: 2,
					  x: tmpLocation[0].x, y: tmpLocation[0].y,
					  radius: 15
					});
				}
				$('#home_panel').drawLine({
					strokeStyle: "#000",
					strokeWidth: 1,
					x1: tmpLocation[tmpLocation.length - 1].x, y1: tmpLocation[tmpLocation.length - 1].y,
					x2: posX, y2: posY
				});
			}
		}
	});
	
	
}); 
