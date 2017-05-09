(function() {
    'use strict';
    angular
        .module('cavmgrApp')
        .factory('ClientActivityDetails', ClientActivityDetails);

    ClientActivityDetails.$inject = ['$resource', 'DateUtils'];

    function ClientActivityDetails ($resource, DateUtils) {
        var resourceUrl =  'api/client-activity-details/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.creationDate = DateUtils.convertDateTimeFromServer(data.creationDate);
                        data.lastUpdateDate = DateUtils.convertDateTimeFromServer(data.lastUpdateDate);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
