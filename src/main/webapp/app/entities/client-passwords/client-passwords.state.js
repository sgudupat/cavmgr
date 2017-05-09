(function() {
    'use strict';

    angular
        .module('cavmgrApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('client-passwords', {
            parent: 'entity',
            url: '/client-passwords',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'cavmgrApp.clientPasswords.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/client-passwords/client-passwords.html',
                    controller: 'ClientPasswordsController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('clientPasswords');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('client-passwords-detail', {
            parent: 'client-passwords',
            url: '/client-passwords/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'cavmgrApp.clientPasswords.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/client-passwords/client-passwords-detail.html',
                    controller: 'ClientPasswordsDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('clientPasswords');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'ClientPasswords', function($stateParams, ClientPasswords) {
                    return ClientPasswords.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'client-passwords',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('client-passwords-detail.edit', {
            parent: 'client-passwords-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/client-passwords/client-passwords-dialog.html',
                    controller: 'ClientPasswordsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ClientPasswords', function(ClientPasswords) {
                            return ClientPasswords.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('client-passwords.new', {
            parent: 'client-passwords',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/client-passwords/client-passwords-dialog.html',
                    controller: 'ClientPasswordsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                accountType: null,
                                userName: null,
                                password: null,
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
                    $state.go('client-passwords', null, { reload: 'client-passwords' });
                }, function() {
                    $state.go('client-passwords');
                });
            }]
        })
        .state('client-passwords.edit', {
            parent: 'client-passwords',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/client-passwords/client-passwords-dialog.html',
                    controller: 'ClientPasswordsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ClientPasswords', function(ClientPasswords) {
                            return ClientPasswords.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('client-passwords', null, { reload: 'client-passwords' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('client-passwords.delete', {
            parent: 'client-passwords',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/client-passwords/client-passwords-delete-dialog.html',
                    controller: 'ClientPasswordsDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['ClientPasswords', function(ClientPasswords) {
                            return ClientPasswords.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('client-passwords', null, { reload: 'client-passwords' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
