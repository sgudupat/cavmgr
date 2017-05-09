(function() {
    'use strict';

    angular
        .module('cavmgrApp')
        .controller('EmployeesDialogController', EmployeesDialogController);

    EmployeesDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Employees'];

    function EmployeesDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Employees) {
        var vm = this;

        vm.employees = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.employees.id !== null) {
                Employees.update(vm.employees, onSaveSuccess, onSaveError);
            } else {
                Employees.save(vm.employees, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('cavmgrApp:employeesUpdate', result);
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
