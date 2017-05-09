(function() {
    'use strict';

    angular
        .module('cavmgrApp')
        .controller('EmployeesController', EmployeesController);

    EmployeesController.$inject = ['Employees'];

    function EmployeesController(Employees) {

        var vm = this;

        vm.employees = [];

        loadAll();

        function loadAll() {
            Employees.query(function(result) {
                vm.employees = result;
                vm.searchQuery = null;
            });
        }
    }
})();
