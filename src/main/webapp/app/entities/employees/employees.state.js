(function() {
    'use strict';

    angular
        .module('cavmgrApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('employees', {
            parent: 'entity',
            url: '/employees',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'cavmgrApp.employees.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/employees/employees.html',
                    controller: 'EmployeesController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('employees');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('employees-detail', {
            parent: 'employees',
            url: '/employees/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'cavmgrApp.employees.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/employees/employees-detail.html',
                    controller: 'EmployeesDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('employees');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Employees', function($stateParams, Employees) {
                    return Employees.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'employees',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('employees-detail.edit', {
            parent: 'employees-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/employees/employees-dialog.html',
                    controller: 'EmployeesDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Employees', function(Employees) {
                            return Employees.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('employees.new', {
            parent: 'employees',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/employees/employees-dialog.html',
                    controller: 'EmployeesDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                password: null,
                                emailAddress: null,
                                mobilePhone: null,
                                status: null,
                                creationDate: null,
                                createdBy: null,
                                lastUpdateDate: null,
                                lastUpatedBy: null,
                                orgId: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('employees', null, { reload: 'employees' });
                }, function() {
                    $state.go('employees');
                });
            }]
        })
        .state('employees.edit', {
            parent: 'employees',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/employees/employees-dialog.html',
                    controller: 'EmployeesDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Employees', function(Employees) {
                            return Employees.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('employees', null, { reload: 'employees' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('employees.delete', {
            parent: 'employees',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/employees/employees-delete-dialog.html',
                    controller: 'EmployeesDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Employees', function(Employees) {
                            return Employees.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('employees', null, { reload: 'employees' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
