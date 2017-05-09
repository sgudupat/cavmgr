(function() {
    'use strict';

    angular
        .module('cavmgrApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('clients', {
            parent: 'entity',
            url: '/clients',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'cavmgrApp.clients.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/clients/clients.html',
                    controller: 'ClientsController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('clients');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('clients-detail', {
            parent: 'clients',
            url: '/clients/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'cavmgrApp.clients.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/clients/clients-detail.html',
                    controller: 'ClientsDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('clients');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Clients', function($stateParams, Clients) {
                    return Clients.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'clients',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('clients-detail.edit', {
            parent: 'clients-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/clients/clients-dialog.html',
                    controller: 'ClientsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Clients', function(Clients) {
                            return Clients.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('clients.new', {
            parent: 'clients',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/clients/clients-dialog.html',
                    controller: 'ClientsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                entityName: null,
                                contactPerson: null,
                                emailAddress: null,
                                officePhone: null,
                                mobilePhone: null,
                                billingPeriod: null,
                                panNumber: null,
                                tanNumber: null,
                                dateOfIncorp: null,
                                grade: null,
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
                    $state.go('clients', null, { reload: 'clients' });
                }, function() {
                    $state.go('clients');
                });
            }]
        })
        .state('clients.edit', {
            parent: 'clients',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/clients/clients-dialog.html',
                    controller: 'ClientsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Clients', function(Clients) {
                            return Clients.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('clients', null, { reload: 'clients' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('clients.delete', {
            parent: 'clients',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/clients/clients-delete-dialog.html',
                    controller: 'ClientsDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Clients', function(Clients) {
                            return Clients.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('clients', null, { reload: 'clients' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
