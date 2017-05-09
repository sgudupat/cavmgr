(function() {
    'use strict';

    angular
        .module('cavmgrApp')
        .controller('ClientActivitiesDetailController', ClientActivitiesDetailController);

    ClientActivitiesDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'ClientActivities', 'Employees', 'Clients'];

    function ClientActivitiesDetailController($scope, $rootScope, $stateParams, previousState, entity, ClientActivities, Employees, Clients) {
        var vm = this;

        vm.clientActivities = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('cavmgrApp:clientActivitiesUpdate', function(event, result) {
            vm.clientActivities = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
