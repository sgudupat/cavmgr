(function() {
    'use strict';

    angular
        .module('cavmgrApp')
        .controller('ClientActivityDetailsDeleteController',ClientActivityDetailsDeleteController);

    ClientActivityDetailsDeleteController.$inject = ['$uibModalInstance', 'entity', 'ClientActivityDetails'];

    function ClientActivityDetailsDeleteController($uibModalInstance, entity, ClientActivityDetails) {
        var vm = this;

        vm.clientActivityDetails = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            ClientActivityDetails.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
