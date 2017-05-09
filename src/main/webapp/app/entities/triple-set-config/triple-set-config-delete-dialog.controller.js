(function() {
    'use strict';

    angular
        .module('cavmgrApp')
        .controller('TripleSetConfigDeleteController',TripleSetConfigDeleteController);

    TripleSetConfigDeleteController.$inject = ['$uibModalInstance', 'entity', 'TripleSetConfig'];

    function TripleSetConfigDeleteController($uibModalInstance, entity, TripleSetConfig) {
        var vm = this;

        vm.tripleSetConfig = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            TripleSetConfig.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
