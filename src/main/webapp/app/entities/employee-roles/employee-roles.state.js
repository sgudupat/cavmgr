(function() {
    'use strict';

    angular
        .module('cavmgrApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('employee-roles', {
            parent: 'entity',
            url: '/employee-roles',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'cavmgrApp.employeeRoles.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/employee-roles/employee-roles.html',
                    controller: 'EmployeeRolesController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('employeeRoles');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('employee-roles-detail', {
            parent: 'employee-roles',
            url: '/employee-roles/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'cavmgrApp.employeeRoles.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/employee-roles/employee-roles-detail.html',
                    controller: 'EmployeeRolesDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('employeeRoles');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'EmployeeRoles', function($stateParams, EmployeeRoles) {
                    return EmployeeRoles.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'employee-roles',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('employee-roles-detail.edit', {
            parent: 'employee-roles-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/employee-roles/employee-roles-dialog.html',
                    controller: 'EmployeeRolesDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['EmployeeRoles', function(EmployeeRoles) {
                            return EmployeeRoles.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('employee-roles.new', {
            parent: 'employee-roles',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/employee-roles/employee-roles-dialog.html',
                    controller: 'EmployeeRolesDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                roleName: null,
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
                    $state.go('employee-roles', null, { reload: 'employee-roles' });
                }, function() {
                    $state.go('employee-roles');
                });
            }]
        })
        .state('employee-roles.edit', {
            parent: 'employee-roles',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/employee-roles/employee-roles-dialog.html',
                    controller: 'EmployeeRolesDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['EmployeeRoles', function(EmployeeRoles) {
                            return EmployeeRoles.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('employee-roles', null, { reload: 'employee-roles' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('employee-roles.delete', {
            parent: 'employee-roles',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/employee-roles/employee-roles-delete-dialog.html',
                    controller: 'EmployeeRolesDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['EmployeeRoles', function(EmployeeRoles) {
                            return EmployeeRoles.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('employee-roles', null, { reload: 'employee-roles' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
