(function() {
    'use strict';

    angular
        .module('cavmgrApp')
        .controller('ClientPasswordsDialogController', ClientPasswordsDialogController);

    ClientPasswordsDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'ClientPasswords', 'Clients'];

    function ClientPasswordsDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, ClientPasswords, Clients) {
        var vm = this;

        vm.clientPasswords = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.clients = Clients.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.clientPasswords.id !== null) {
                ClientPasswords.update(vm.clientPasswords, onSaveSuccess, onSaveError);
            } else {
                ClientPasswords.save(vm.clientPasswords, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('cavmgrApp:clientPasswordsUpdate', result);
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
