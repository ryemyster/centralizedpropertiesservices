/**
 * 
 */
(function() {

	//
	var app = angular.module('propteriesApp', []);

	//
	var control = function($scope, $http) {

		var store = this;
		store.instances = [];
		store.showAdd = false;
		store.propertyNumber = 0;
		store.newInstance = {};
		store.newProperties = [];
		store.tempProperty = {};

		/**
		 * 
		 */
		$http
				.get(
						'/PropertiesServices-1.0/properties/propertiesmanager/properties/instances')
				.success(function(data) {

					// verify instances were found
					if (data.length > 0) {
						store.buildChecked(data);
					}

				}).error(function(data) {
					// console.log('Error: --' + data);
				});

		/**
		 * 
		 */
		store.addInstance = function(instance) {

			// verify instance object
			console.log("Adding instance...");

			// send request
			$http
					.post(
							'/PropertiesServices-1.0/properties/propertiesmanager/properties/add',
							instance).success(function(data) {
						console.log("Success " + data);
					}).error(function(data) {
						// console.log('Error: --' + data);
					});
		};

		/**
		 * TODO: What's wrong w/ the properties looping?
		 */
		store.buildChecked = function(list) {

			var sObj = [];

			for (index in list) {

				var item = JSON.parse(list[index]);

				item.id = item.instance_Id;

				item.index = index;
				item.isChecked = false;

				sObj[index] = JSON.parse(JSON.stringify(item));
				console.log(JSON.stringify(item));

			}

			store.instances = sObj;
			console.log(JSON.stringify(store.instances));

		};

		/**
		 * 
		 */
		store.getNumber = function() {
			return new Array(store.propertyNumber);
		};

		/**
		 * 
		 */
		store.hasChecked = function(instances) {

			for (index in instances) {
				// console.log(instances[index].isChecked);
				if (instances[index].isChecked) {
					return true;
				}
			}
			return false;
		};

		/**
		 * 
		 */
		store.setMaster = function(section) {
			$scope.selected = section;
		};

		/**
		 * 
		 */
		store.isSelected = function(section) {
			return $scope.selected === section;
		};

		/**
		 * CLEAN UP LATER
		 * 
		 * TODO: HOW TO SEPARATE THE ADD INSTANCE FROM THE BUILD CHECKED AND
		 * REFRESH THE LIST
		 * 
		 */
		store.save = function() {

			// TODO: error handling, figure out how to check for
			// empty
			// have angular decide what can't be empty on
			// submit.

			// console.log(JSON.stringify(store.newInstance));

			// HACK FOR NOW to fix temp object
			replacer = {};
			for (var i = 0; i < store.newProperties.length; i++) {
				replacer = store.newProperties[i].tempProperty;
			}

			store.newInstance.properties = replacer;

			console.log(JSON.stringify(store.newInstance));

			// update list and refresh..how to do this with
			// angular?
			// store.instances.push(store.newInstance);

			store.addInstance(store.newInstance);
			console.log('reload');
			location.reload();

		};

		/**
		 * 
		 */
		store.incrementValue = function() {
			// console.log("Incrementing - "
			// + store.propertyNumber);

			store.newProperties[store.propertyNumber] = store.tempProperty;

			++store.propertyNumber;

			store.tempProperty = {};

			// console.log("Building.."
			// + JSON.stringify(store.newProperties));

		};

		/**
		 * CHANGE TO HANDLE VALUE AND THE PROPERTY REMOVAL
		 */
		store.decrementValue = function() {
			// console.log("Decrement - " +
			// store.propertyNumber);
			--store.propertyNumber;

			store.newProperties.splice(store.newProperties.length - 1, 1);

			if (store.propertyNumber <= 0) {
				store.propertyNumber = 0;
			}
		};

		/**
		 * RESET ALL DATA
		 */
		store.cancelNewInstance = function() {
			store.showAdd = false;
			store.propertyNumber = 0;
			store.newInstance = {};
			store.newProperties = [];
		};
	};

	/**
	 * @param Controller
	 *            Name
	 * @param callback
	 */
	app.controller('PropertiesController', control);

})();