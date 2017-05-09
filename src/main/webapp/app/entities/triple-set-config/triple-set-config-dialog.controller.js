(function() {
    'use strict';

    angular
        .module('cavmgrApp')
        .controller('TripleSetConfigDialogController', TripleSetConfigDialogController);

    TripleSetConfigDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'TripleSetConfig'];

    function TripleSetConfigDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, TripleSetConfig) {
        var vm = this;

        vm.tripleSetConfig = entity;
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
            if (vm.tripleSetConfig.id !== null) {
                TripleSetConfig.update(vm.tripleSetConfig, onSaveSuccess, onSaveError);
            } else {
                TripleSetConfig.save(vm.tripleSetConfig, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('cavmgrApp:tripleSetConfigUpdate', result);
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
