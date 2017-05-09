(function() {
    'use strict';

    angular
        .module('cavmgrApp')
        .controller('ClientPasswordsController', ClientPasswordsController);

    ClientPasswordsController.$inject = ['ClientPasswords'];

    function ClientPasswordsController(ClientPasswords) {

        var vm = this;

        vm.clientPasswords = [];

        loadAll();

        function loadAll() {
            ClientPasswords.query(function(result) {
                vm.clientPasswords = result;
                vm.searchQuery = null;
            });
        }
    }
})();
