(function() {
    'use strict';

    angular
        .module('cavmgrApp')
        .controller('ClientActivitiesController', ClientActivitiesController);

    ClientActivitiesController.$inject = ['ClientActivities'];

    function ClientActivitiesController(ClientActivities) {

        var vm = this;

        vm.clientActivities = [];

        loadAll();

        function loadAll() {
            ClientActivities.query(function(result) {
                vm.clientActivities = result;
                vm.searchQuery = null;
            });
        }
    }
})();
