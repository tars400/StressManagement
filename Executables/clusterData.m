clear all
close all

dataset = importfile('123_dataset.xlsx','Sheet1','B2:BI3015');
[idx,clt]=kmeans(dataset,3);
% "idx" will have a number 1,2 or 3 for every row, which is nothing but
% cluster number that gives the cluster to which that particular row belongs
% to.  "clt" is the centroid of the 3 different clusters.
% Lets consider you have a new data, that is test data. You have to check
% to which cluster that data belongs to,which can be found by using
% Eucledian distance of new data from the centroid of 3 clusters. The
% minimum distance tells to which cluster the new data belongs to.

rowNumber=1235;
testData=dataset(rowNumber,:);
eucledianDist=zeros(1,3);
for i=1:3
    eucledianDist(i)=sqrt(sum((clt(i,:)-testData).^2));
end

testCluster=find(eucledianDist==min(eucledianDist))
kmeansOutput=idx(rowNumber)
% testCluster has the cluster number to which testData belongs to  