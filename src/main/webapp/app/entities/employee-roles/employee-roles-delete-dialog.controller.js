(function() {
    'use strict';

    angular
        .module('cavmgrApp')
        .controller('EmployeeRolesDeleteController',EmployeeRolesDeleteController);

    EmployeeRolesDeleteController.$inject = ['$uibModalInstance', 'entity', 'EmployeeRoles'];

    function EmployeeRolesDeleteController($uibModalInstance, entity, EmployeeRoles) {
        var vm = this;

        vm.employeeRoles = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            EmployeeRoles.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
