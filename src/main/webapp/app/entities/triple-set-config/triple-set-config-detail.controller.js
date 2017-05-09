(function() {
    'use strict';

    angular
        .module('cavmgrApp')
        .controller('TripleSetConfigDetailController', TripleSetConfigDetailController);

    TripleSetConfigDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'TripleSetConfig'];

    function TripleSetConfigDetailController($scope, $rootScope, $stateParams, previousState, entity, TripleSetConfig) {
        var vm = this;

        vm.tripleSetConfig = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('cavmgrApp:tripleSetConfigUpdate', function(event, result) {
            vm.tripleSetConfig = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
