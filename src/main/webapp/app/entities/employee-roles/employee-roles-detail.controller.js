(function() {
    'use strict';

    angular
        .module('cavmgrApp')
        .controller('EmployeeRolesDetailController', EmployeeRolesDetailController);

    EmployeeRolesDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'EmployeeRoles', 'Employees'];

    function EmployeeRolesDetailController($scope, $rootScope, $stateParams, previousState, entity, EmployeeRoles, Employees) {
        var vm = this;

        vm.employeeRoles = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('cavmgrApp:employeeRolesUpdate', function(event, result) {
            vm.employeeRoles = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
