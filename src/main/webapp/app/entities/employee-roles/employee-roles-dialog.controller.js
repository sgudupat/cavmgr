(function() {
    'use strict';

    angular
        .module('cavmgrApp')
        .controller('EmployeeRolesDialogController', EmployeeRolesDialogController);

    EmployeeRolesDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'EmployeeRoles', 'Employees'];

    function EmployeeRolesDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, EmployeeRoles, Employees) {
        var vm = this;

        vm.employeeRoles = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.employees = Employees.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.employeeRoles.id !== null) {
                EmployeeRoles.update(vm.employeeRoles, onSaveSuccess, onSaveError);
            } else {
                EmployeeRoles.save(vm.employeeRoles, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('cavmgrApp:employeeRolesUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.creationDate = false;
        vm.datePickerOpenStatus.lastUpdateDate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
