'use strict';

describe('Controller Tests', function() {

    describe('ClientActivities Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockClientActivities, MockEmployees, MockClients;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockClientActivities = jasmine.createSpy('MockClientActivities');
            MockEmployees = jasmine.createSpy('MockEmployees');
            MockClients = jasmine.createSpy('MockClients');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'ClientActivities': MockClientActivities,
                'Employees': MockEmployees,
                'Clients': MockClients
            };
            createController = function() {
                $injector.get('$controller')("ClientActivitiesDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'cavmgrApp:clientActivitiesUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
