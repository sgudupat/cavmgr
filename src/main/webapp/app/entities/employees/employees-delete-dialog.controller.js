(function() {
    'use strict';

    angular
        .module('cavmgrApp')
        .controller('EmployeesDeleteController',EmployeesDeleteController);

    EmployeesDeleteController.$inject = ['$uibModalInstance', 'entity', 'Employees'];

    function EmployeesDeleteController($uibModalInstance, entity, Employees) {
        var vm = this;

        vm.employees = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Employees.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
