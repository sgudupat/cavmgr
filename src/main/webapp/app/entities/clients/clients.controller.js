(function() {
    'use strict';

    angular
        .module('cavmgrApp')
        .controller('ClientsController', ClientsController);

    ClientsController.$inject = ['Clients'];

    function ClientsController(Clients) {

        var vm = this;

        vm.clients = [];

        loadAll();

        function loadAll() {
            Clients.query(function(result) {
                vm.clients = result;
                vm.searchQuery = null;
            });
        }
    }
})();
