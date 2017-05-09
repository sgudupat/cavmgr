(function() {
    'use strict';

    angular
        .module('cavmgrApp')
        .controller('ClientPasswordsDetailController', ClientPasswordsDetailController);

    ClientPasswordsDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'ClientPasswords', 'Clients'];

    function ClientPasswordsDetailController($scope, $rootScope, $stateParams, previousState, entity, ClientPasswords, Clients) {
        var vm = this;

        vm.clientPasswords = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('cavmgrApp:clientPasswordsUpdate', function(event, result) {
            vm.clientPasswords = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
