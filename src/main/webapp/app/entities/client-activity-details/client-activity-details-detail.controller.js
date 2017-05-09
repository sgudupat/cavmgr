(function() {
    'use strict';

    angular
        .module('cavmgrApp')
        .controller('ClientActivityDetailsDetailController', ClientActivityDetailsDetailController);

    ClientActivityDetailsDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'ClientActivityDetails', 'Employees', 'Clients'];

    function ClientActivityDetailsDetailController($scope, $rootScope, $stateParams, previousState, entity, ClientActivityDetails, Employees, Clients) {
        var vm = this;

        vm.clientActivityDetails = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('cavmgrApp:clientActivityDetailsUpdate', function(event, result) {
            vm.clientActivityDetails = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
