(function() {
    'use strict';

    angular
        .module('cavmgrApp')
        .controller('ClientPasswordsDeleteController',ClientPasswordsDeleteController);

    ClientPasswordsDeleteController.$inject = ['$uibModalInstance', 'entity', 'ClientPasswords'];

    function ClientPasswordsDeleteController($uibModalInstance, entity, ClientPasswords) {
        var vm = this;

        vm.clientPasswords = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            ClientPasswords.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
