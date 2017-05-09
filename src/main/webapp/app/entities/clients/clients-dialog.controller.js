(function() {
    'use strict';

    angular
        .module('cavmgrApp')
        .controller('ClientsDialogController', ClientsDialogController);

    ClientsDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Clients'];

    function ClientsDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Clients) {
        var vm = this;

        vm.clients = entity;
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
            if (vm.clients.id !== null) {
                Clients.update(vm.clients, onSaveSuccess, onSaveError);
            } else {
                Clients.save(vm.clients, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('cavmgrApp:clientsUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.dateOfIncorp = false;
        vm.datePickerOpenStatus.creationDate = false;
        vm.datePickerOpenStatus.lastUpdateDate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
