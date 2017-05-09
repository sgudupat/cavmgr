(function() {
    'use strict';

    angular
        .module('cavmgrApp')
        .controller('TripleSetConfigController', TripleSetConfigController);

    TripleSetConfigController.$inject = ['TripleSetConfig'];

    function TripleSetConfigController(TripleSetConfig) {

        var vm = this;

        vm.tripleSetConfigs = [];

        loadAll();

        function loadAll() {
            TripleSetConfig.query(function(result) {
                vm.tripleSetConfigs = result;
                vm.searchQuery = null;
            });
        }
    }
})();
