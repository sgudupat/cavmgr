(function() {
    'use strict';

    angular
        .module('cavmgrApp')
        .controller('ClientActivitiesDeleteController',ClientActivitiesDeleteController);

    ClientActivitiesDeleteController.$inject = ['$uibModalInstance', 'entity', 'ClientActivities'];

    function ClientActivitiesDeleteController($uibModalInstance, entity, ClientActivities) {
        var vm = this;

        vm.clientActivities = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            ClientActivities.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
