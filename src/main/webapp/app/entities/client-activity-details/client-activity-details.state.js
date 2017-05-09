(function() {
    'use strict';

    angular
        .module('cavmgrApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('client-activity-details', {
            parent: 'entity',
            url: '/client-activity-details',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'cavmgrApp.clientActivityDetails.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/client-activity-details/client-activity-details.html',
                    controller: 'ClientActivityDetailsController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('clientActivityDetails');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('client-activity-details-detail', {
            parent: 'client-activity-details',
            url: '/client-activity-details/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'cavmgrApp.clientActivityDetails.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/client-activity-details/client-activity-details-detail.html',
                    controller: 'ClientActivityDetailsDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('clientActivityDetails');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'ClientActivityDetails', function($stateParams, ClientActivityDetails) {
                    return ClientActivityDetails.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'client-activity-details',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('client-activity-details-detail.edit', {
            parent: 'client-activity-details-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/client-activity-details/client-activity-details-dialog.html',
                    controller: 'ClientActivityDetailsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ClientActivityDetails', function(ClientActivityDetails) {
                            return ClientActivityDetails.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('client-activity-details.new', {
            parent: 'client-activity-details',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/client-activity-details/client-activity-details-dialog.html',
                    controller: 'ClientActivityDetailsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                dueDate: null,
                                internalDueDate: null,
                                status: null,
                                comments: null,
                                workStatus: null,
                                monthYear: null,
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
                    $state.go('client-activity-details', null, { reload: 'client-activity-details' });
                }, function() {
                    $state.go('client-activity-details');
                });
            }]
        })
        .state('client-activity-details.edit', {
            parent: 'client-activity-details',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/client-activity-details/client-activity-details-dialog.html',
                    controller: 'ClientActivityDetailsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ClientActivityDetails', function(ClientActivityDetails) {
                            return ClientActivityDetails.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('client-activity-details', null, { reload: 'client-activity-details' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('client-activity-details.delete', {
            parent: 'client-activity-details',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/client-activity-details/client-activity-details-delete-dialog.html',
                    controller: 'ClientActivityDetailsDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['ClientActivityDetails', function(ClientActivityDetails) {
                            return ClientActivityDetails.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('client-activity-details', null, { reload: 'client-activity-details' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
