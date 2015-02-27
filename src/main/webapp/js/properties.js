$(document).ready(function() {

	var queue = false;
	var file = false;
	var database = false;

	$('#saveButton').click(function() {
		addProperties(file, database, queue);
	});

	$('#serverStatus').click(function() {
		checkStatus();
	});

	// TODO: Update time display dynamically every 30 seconds
	$('#timeDiv').load(function() {
		document.write(Date());
	})
});

// TODO Change to a looping service that just displays an icon as green or red
// and checks every minute for connectivity
function checkStatus() {
	var END_POINT = 'http://localhost:8080/PropertiesService-1.0/properties/propertiesmanager/status';

	$.ajax({
		url : END_POINT,
		type : 'GET',
		contentType : 'application/text',
		success : function() {
			alert("Server is Up!");
			// TODO: GREEN DOT ICON
		},
		error : function() {
			alert("Server is DOWN!");
			// TODO: RED DOT ICON
		}
	});

}

function isNullOrEmptyOrUndefined(variable) {
	return ((variable === null) || (variable === undefined) || (!variable
			.trim()));
}

function addProperties(file, database, queue) {

	if ($('#queueBox').is(':checked')) {
		queue = true;
	}

	if ($('#fileBox').is(':checked')) {
		file = true;
	}

	if ($('#databaseBox').is(':checked')) {
		database = true;
	}

	// TODO: don't hardcode!
	// Base Endpoint URL
	var END_POINT = 'http://localhost:8080/PropertiesService-1.0/properties/propertiesmanager';

	// Update for persistence
	var PERSIST_STATUS_URL = END_POINT + '/storage/persist?';
	var PERSIST_URL = PERSIST_STATUS_URL + "&persistFile=" + file
			+ "&persistDatabase=" + database + "&persistQueue=" + queue;

	// Send properties
	var PROPERTIES_URL = END_POINT + '/properties/add';
	var json = $('#propertiesJSON').val();

	var isNullorEmpty = isNullOrEmptyOrUndefined(json);

	if (!isNullorEmpty) {
		json = json.replace(/(\r|\n|\t)+/, " ").trim();
		console.log(json);
		var payload = JSON.parse(json.replace(/(\r|\n|\t)+/, "\n").trim());

		// update persistence state based on checkbox
		$.ajax({
			url : PERSIST_URL,
			type : 'POST',
			contentType : 'application/text'
		});

		// send properties
		$.ajax({
			url : PROPERTIES_URL,
			type : 'POST',
			contentType : 'application/json',
			dataType : 'jsonp',
			data : json
		});
	} else {
		alert("Properties Must not be Empty.");
	}

	// $("#propertiesJSON").data("JSON", $('#propertiesJSON').val());

}
