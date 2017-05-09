(function() {
    'use strict';

    angular
        .module('cavmgrApp')
        .controller('ClientActivitiesDialogController', ClientActivitiesDialogController);

    ClientActivitiesDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'ClientActivities', 'Employees', 'Clients'];

    function ClientActivitiesDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, ClientActivities, Employees, Clients) {
        var vm = this;

        vm.clientActivities = entity;
        vm.clear = clear;
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
            if (vm.clientActivities.id !== null) {
                ClientActivities.update(vm.clientActivities, onSaveSuccess, onSaveError);
            } else {
                ClientActivities.save(vm.clientActivities, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('cavmgrApp:clientActivitiesUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
