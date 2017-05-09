(function() {
    'use strict';

    angular
        .module('cavmgrApp')
        .controller('EmployeesDetailController', EmployeesDetailController);

    EmployeesDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Employees'];

    function EmployeesDetailController($scope, $rootScope, $stateParams, previousState, entity, Employees) {
        var vm = this;

        vm.employees = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('cavmgrApp:employeesUpdate', function(event, result) {
            vm.employees = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
