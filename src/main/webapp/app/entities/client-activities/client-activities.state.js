(function() {
    'use strict';

    angular
        .module('cavmgrApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('client-activities', {
            parent: 'entity',
            url: '/client-activities',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'cavmgrApp.clientActivities.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/client-activities/client-activities.html',
                    controller: 'ClientActivitiesController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('clientActivities');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('client-activities-detail', {
            parent: 'client-activities',
            url: '/client-activities/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'cavmgrApp.clientActivities.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/client-activities/client-activities-detail.html',
                    controller: 'ClientActivitiesDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('clientActivities');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'ClientActivities', function($stateParams, ClientActivities) {
                    return ClientActivities.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'client-activities',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('client-activities-detail.edit', {
            parent: 'client-activities-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/client-activities/client-activities-dialog.html',
                    controller: 'ClientActivitiesDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ClientActivities', function(ClientActivities) {
                            return ClientActivities.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('client-activities.new', {
            parent: 'client-activities',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/client-activities/client-activities-dialog.html',
                    controller: 'ClientActivitiesDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                activityName: null,
                                priority: null,
                                applicability: null,
                                orgId: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('client-activities', null, { reload: 'client-activities' });
                }, function() {
                    $state.go('client-activities');
                });
            }]
        })
        .state('client-activities.edit', {
            parent: 'client-activities',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/client-activities/client-activities-dialog.html',
                    controller: 'ClientActivitiesDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ClientActivities', function(ClientActivities) {
                            return ClientActivities.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('client-activities', null, { reload: 'client-activities' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('client-activities.delete', {
            parent: 'client-activities',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/client-activities/client-activities-delete-dialog.html',
                    controller: 'ClientActivitiesDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['ClientActivities', function(ClientActivities) {
                            return ClientActivities.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('client-activities', null, { reload: 'client-activities' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
