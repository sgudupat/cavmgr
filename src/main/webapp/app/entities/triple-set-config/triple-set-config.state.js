(function() {
    'use strict';

    angular
        .module('cavmgrApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('triple-set-config', {
            parent: 'entity',
            url: '/triple-set-config',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'cavmgrApp.tripleSetConfig.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/triple-set-config/triple-set-configs.html',
                    controller: 'TripleSetConfigController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('tripleSetConfig');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('triple-set-config-detail', {
            parent: 'triple-set-config',
            url: '/triple-set-config/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'cavmgrApp.tripleSetConfig.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/triple-set-config/triple-set-config-detail.html',
                    controller: 'TripleSetConfigDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('tripleSetConfig');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'TripleSetConfig', function($stateParams, TripleSetConfig) {
                    return TripleSetConfig.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'triple-set-config',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('triple-set-config-detail.edit', {
            parent: 'triple-set-config-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/triple-set-config/triple-set-config-dialog.html',
                    controller: 'TripleSetConfigDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TripleSetConfig', function(TripleSetConfig) {
                            return TripleSetConfig.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('triple-set-config.new', {
            parent: 'triple-set-config',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/triple-set-config/triple-set-config-dialog.html',
                    controller: 'TripleSetConfigDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                control: null,
                                parent: null,
                                child: null,
                                config: null,
                                creationDate: null,
                                createdBy: null,
                                lastUpdateDate: null,
                                lastUpatedBy: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('triple-set-config', null, { reload: 'triple-set-config' });
                }, function() {
                    $state.go('triple-set-config');
                });
            }]
        })
        .state('triple-set-config.edit', {
            parent: 'triple-set-config',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/triple-set-config/triple-set-config-dialog.html',
                    controller: 'TripleSetConfigDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TripleSetConfig', function(TripleSetConfig) {
                            return TripleSetConfig.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('triple-set-config', null, { reload: 'triple-set-config' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('triple-set-config.delete', {
            parent: 'triple-set-config',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/triple-set-config/triple-set-config-delete-dialog.html',
                    controller: 'TripleSetConfigDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['TripleSetConfig', function(TripleSetConfig) {
                            return TripleSetConfig.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('triple-set-config', null, { reload: 'triple-set-config' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
