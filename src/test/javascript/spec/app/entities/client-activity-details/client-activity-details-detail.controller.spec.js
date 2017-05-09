'use strict';

describe('Controller Tests', function() {

    describe('ClientActivityDetails Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockClientActivityDetails, MockEmployees, MockClients;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockClientActivityDetails = jasmine.createSpy('MockClientActivityDetails');
            MockEmployees = jasmine.createSpy('MockEmployees');
            MockClients = jasmine.createSpy('MockClients');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'ClientActivityDetails': MockClientActivityDetails,
                'Employees': MockEmployees,
                'Clients': MockClients
            };
            createController = function() {
                $injector.get('$controller')("ClientActivityDetailsDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'cavmgrApp:clientActivityDetailsUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
