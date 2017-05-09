(function() {
    'use strict';

    angular
        .module('cavmgrApp')
        .controller('ClientsDetailController', ClientsDetailController);

    ClientsDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Clients'];

    function ClientsDetailController($scope, $rootScope, $stateParams, previousState, entity, Clients) {
        var vm = this;

        vm.clients = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('cavmgrApp:clientsUpdate', function(event, result) {
            vm.clients = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
