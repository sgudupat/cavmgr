(function() {
    'use strict';

    angular
        .module('cavmgrApp')
        .controller('ClientActivityDetailsController', ClientActivityDetailsController);

    ClientActivityDetailsController.$inject = ['ClientActivityDetails'];

    function ClientActivityDetailsController(ClientActivityDetails) {

        var vm = this;

        vm.clientActivityDetails = [];

        loadAll();

        function loadAll() {
            ClientActivityDetails.query(function(result) {
                vm.clientActivityDetails = result;
                vm.searchQuery = null;
            });
        }
    }
})();
