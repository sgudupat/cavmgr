(function() {
    'use strict';

    angular
        .module('cavmgrApp')
        .controller('EmployeeRolesController', EmployeeRolesController);

    EmployeeRolesController.$inject = ['EmployeeRoles'];

    function EmployeeRolesController(EmployeeRoles) {

        var vm = this;

        vm.employeeRoles = [];

        loadAll();

        function loadAll() {
            EmployeeRoles.query(function(result) {
                vm.employeeRoles = result;
                vm.searchQuery = null;
            });
        }
    }
})();
