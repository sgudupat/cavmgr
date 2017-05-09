'use strict';

describe('Controller Tests', function() {

    describe('EmployeeRoles Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockEmployeeRoles, MockEmployees;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockEmployeeRoles = jasmine.createSpy('MockEmployeeRoles');
            MockEmployees = jasmine.createSpy('MockEmployees');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'EmployeeRoles': MockEmployeeRoles,
                'Employees': MockEmployees
            };
            createController = function() {
                $injector.get('$controller')("EmployeeRolesDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'cavmgrApp:employeeRolesUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
