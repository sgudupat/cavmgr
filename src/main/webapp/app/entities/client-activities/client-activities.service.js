(function() {
    'use strict';
    angular
        .module('cavmgrApp')
        .factory('ClientActivities', ClientActivities);

    ClientActivities.$inject = ['$resource'];

    function ClientActivities ($resource) {
        var resourceUrl =  'api/client-activities/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
