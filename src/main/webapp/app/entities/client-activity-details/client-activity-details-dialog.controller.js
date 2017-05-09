(function() {
    'use strict';

    angular
        .module('cavmgrApp')
        .controller('ClientActivityDetailsDialogController', ClientActivityDetailsDialogController);

    ClientActivityDetailsDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'ClientActivityDetails', 'Employees', 'Clients'];

    function ClientActivityDetailsDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, ClientActivityDetails, Employees, Clients) {
        var vm = this;

        vm.clientActivityDetails = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.employees = Employees.query();
        vm.clients = Clients.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.clientActivityDetails.id !== null) {
                ClientActivityDetails.update(vm.clientActivityDetails, onSaveSuccess, onSaveError);
            } else {
                ClientActivityDetails.save(vm.clientActivityDetails, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('cavmgrApp:clientActivityDetailsUpdate', result);
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
